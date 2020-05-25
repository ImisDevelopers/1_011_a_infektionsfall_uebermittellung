/**
 * Module providing utility mixins for creating wrapper controls or form
 * control groups operating with AntDV forms.
 */

import Vue, { PropType } from 'vue'
import * as typing from '@/util/typing'

// >>>>> HELPER FUNCTION & DECLARATION SECTION >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

// Realise camelCase prefixing, whereas the prefixed name starts with capital
function prefixed(name: string, prefix?: string): string {
  if (!prefix) {
    return name
  } else {
    return prefix + name.charAt(0).toUpperCase() + name.substring(1)
  }
}

interface MinimalFormContext {
  form: {
    formItems: Record<
      string,
      {
        itemSelfUpdate: boolean
      }
    >
    setFieldsValue(vals: Record<string, any>): void
    getFieldsValue(names?: string[]): Record<string, any>
  }
}

// >>>>> MIXIN DEFINITIONS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

declare module 'vue/types/options' {
  interface ComponentOptions<V extends Vue> {
    fieldIdentifiers?: string[]
  }
}

export interface FormGroupMixin {
  $options: {
    /**
     * Array of all the field identifiers used for the fields managed by
     * this form group component.
     */
    fieldIdentifiers?: string[]
  }

  $props: {
    /**
     * Optional prefix to be applied to all field names.
     */
    fieldNamePrefix?: string
    /**
     * Optional mapping of field identifiers to custom field names for some
     * or all fields. The names of fields not specified in this map are subject
     * to default field naming, respecting a provided field name prefix.
     */
    fieldNames?: Record<string, string>
    /**
     * Optional props to apply to some or all fields. The top level key
     * is the identifier of the form field with a map of the props to apply
     * to that field as the respective value.
     */
    controlProps?: Record<string, Record<string, any>>
  }

  /**
   * Generates the name to be used for the field with the given identifier,
   * according to field name overrides (see inputKeys prop) and field name
   * prefixing (see inputKeyPrefix prop). This function is typically called
   * to generate the appropriate field name for the v-decorator directive.
   */
  formFieldName(fieldId: string): string

  /**
   * Sets some or all fields of this form group. The second parameter specifies
   * whether the passed key-value mapping's keys specify field identifiers or
   * the actual field names as used in the form.
   */
  setData(data: Record<string, any>, usesFormFieldNames?: boolean): void
  /**
   * Retrieves the values of some or all fields of this form group. The optional
   * second parameter specifies whether the given field name array specifies
   * field identifiers or the actual field names as used in the form. The result
   * mapping will map the values by the same names.
   */
  getData(
    fieldNames?: string[],
    usesFormFieldNames?: boolean
  ): Record<string, any>
  /**
   * Retrieves the value of one specific field of this form group. The optional
   * second parameter specifies whether the given field name array specifies
   * field identifiers or the actual field names as used in the form.
   */
  getSingleValue(
    fieldName: string,
    usesFormFieldNames?: boolean
  ): any | undefined
}

/**
 * Mixin providing utility functionality like prefixing and re-mapping field
 * names and accessing field values for form group components.
 *
 * For using this mixin effectively, you have to
 *
 *  - specify field identifiers for all the fields that shall be managed by the
 *    implementing form group by defining the `fieldIdentifiers` option with an
 *    array of those names. Field identifiers are quite similar to form field
 *    names, but may not be unique across the whole form. Therefore, field
 *    identifiers are only valid within an instance of the form group where
 *    they can be used to access the managed fields' values.
 *
 *  - use the `formFieldName` function in field decorator definitions that will
 *    translate the given assigned field identifier into the field's name within
 *    the form. By doing so, components using the form group can adapt the names
 *    of the form group instance's fields as required by external conditions and
 *    uniqueness across the form.
 *
 * See type interface for information about exposed API.
 */
export const FormGroupMixin = Vue.extend({
  props: {
    fieldNamePrefix: {
      type: String as PropType<string>,
      default: undefined,
    },
    fieldIdentifiers: {
      type: Object as PropType<Record<string, string>>,
      default: () => ({}),
    },
    inputProps: {
      type: Object as PropType<Record<string, Record<string, any>>>,
      default(): Record<string, Record<string, any>> {
        return Object.fromEntries(
          this.$options.fieldIdentifiers!.map((key: string) => [key, {}])
        )
      },
    },
  },
  data() {
    return {
      formFieldNameBackTranslation: {} as Record<string, string>,
    }
  },
  inject: {
    FormContext: { default: () => ({}) },
  },
  created() {
    if (this.$options.fieldIdentifiers) {
      this.$options.fieldIdentifiers.forEach((fieldId: string) => {
        this.formFieldNameBackTranslation[this.formFieldName(fieldId)] = fieldId
      })
    }
  },
  mounted(): void {
    if (this.$options.fieldIdentifiers) {
      // Make sure all form items use selfUpdate; this is crucial for form items
      // in the group to be re-rendered correctly when new values are set
      this.$options.fieldIdentifiers.forEach((key: string) => {
        if (
          this.getFormContext().form.formItems[this.formFieldName(key)]
            .itemSelfUpdate
        ) {
          console.error(
            `[ ${this.$options.name} ]: ` +
              `\`itemSelfUpdate\` is not enabled for form item of \`${key}\`. ` +
              'This may lead to contents not being re-rendered when their ' +
              'value is modified by calling `setFieldsValue` on its ' +
              'containing form. To fix this, add `:selfUpdate="true"` to ' +
              `the \`a-form-item\` component containing the \`${key}\` control ` +
              'or add `:selfUpdate="true"` to the root `a-form` element.'
          )
        }
      })
    }
  },
  methods: {
    getFormContext() {
      return typing.extended(
        this,
        typing.TypeArg<{
          FormContext: MinimalFormContext
        }>()
      ).FormContext
    },
    formFieldName(key: string): string {
      const propKeys = this.$props.fieldNames
      return propKeys && Object.prototype.hasOwnProperty.call(propKeys, key)
        ? propKeys[key]
        : prefixed(key, this.$props.fieldNamePrefix)
    },
    setData(data: Record<string, any>, usesFormFieldNames?: boolean) {
      if (!this.$options.fieldIdentifiers) {
        throw new Error(`[ ${this.$options.name} ]: \`setData\` not supported`)
      }

      // Check if conversion of identifiers to field names is required
      if (!usesFormFieldNames) {
        data = Object.fromEntries(
          Object.entries(data).map((entry: [string, any]) => [
            this.formFieldName(entry[0]),
            entry[1],
          ])
        )
      }

      // Filter out any names that do not have a corresponding identifier
      // defined using the fieldIdentifiers option
      const fieldNames = this.$options.fieldIdentifiers.map(this.formFieldName)
      data = Object.fromEntries(
        Object.entries(data).filter((value: [string, any]) =>
          fieldNames.includes(value[0])
        )
      )

      // Set the values
      this.getFormContext().form.setFieldsValue(data)
    },
    getData(
      fieldNames?: string[],
      usesFormFieldNames?: boolean
    ): Record<string, any> {
      if (!this.$options.fieldIdentifiers) {
        throw new Error(`[ ${this.$options.name} ]: \`getData\` not supported`)
      }

      if (fieldNames !== undefined && !usesFormFieldNames) {
        // fieldNames contains field identifiers that need to be translated
        fieldNames = fieldNames.map((fieldId: string) => {
          const formFieldName = this.formFieldName(fieldId)
          return formFieldName
        })
      }

      const permittedFieldNames = this.$options.fieldIdentifiers.map(
        this.formFieldName
      )
      if (fieldNames === undefined) {
        // No specific field names specified is equivalent to getting them all
        fieldNames = permittedFieldNames
      } else {
        // Filter out any names that do not have a corresponding identifier
        // defined using the fieldIdentifiers option
        fieldNames = fieldNames.filter((fieldName: string) =>
          permittedFieldNames.includes(fieldName)
        )
      }

      // Retrieve the requested values; if field names have been specified
      // as field identifiers, the result's keys will be translated back
      let result = this.getFormContext().form.getFieldsValue(fieldNames)
      if (!usesFormFieldNames) {
        result = Object.fromEntries(
          Object.entries(result).map((entry: [string, any]) => [
            this.formFieldNameBackTranslation[entry[0]],
            entry[1],
          ])
        )
      }

      return result
    },
    getSingleValue(
      fieldName: string,
      usesFormFieldNames?: boolean
    ): any | undefined {
      const key = usesFormFieldNames ? fieldName : this.formFieldName(fieldName)
      return this.getData([key], true)[key]
    },
  },
})

declare module 'vue/types/options' {
  interface ComponentOptions<V extends Vue> {
    fieldValueConvert?(value: any): any
  }
}

export interface FormControlMixin {
  $options: {
    /**
     * Optional function for converting values into this control's native value
     * format. It will be automatically called whenever some value is applied
     * to this control, e.g. by a `setFieldsValue` on the supporting form. It is
     * therefore well-suited for wrapper controls that want to support additional
     * value types to the ones allowed by the wrapped control.
     *
     * The implementor of this function has to ensure that this function behaves
     * like the identity function for the native value format, since failure to do
     * so may lead to infinite recursion.
     */
    fieldValueConvert?(value: any): any
  }

  /**
   * The name of this form control as specified by field decorator.
   */
  fieldName: string | null

  /**
   * Returns whether this form control is used in conjunction with a field decorator.
   */
  isDecoratedFormField(): boolean

  /**
   * Sets the value of this control.
   */
  setOwnValue(value: any): void
  /**
   * Retrieves the current value of this control.
   */
  getOwnValue(): any
}

/**
 * Mixin providing utility functionality to form controls or AntDV form control
 * wrappers.
 *
 * See type interface for information about the exposed API.
 */
export const FormControlMixin = Vue.extend({
  inject: {
    FormContext: { default: () => ({}) },
  },
  computed: {
    fieldName(): string | null {
      if (this.getFormContext() && this.$attrs['data-__field']) {
        return typing.cast<{ name: string }>(this.$attrs['data-__field']).name
      } else {
        return null
      }
    },
  },
  data() {
    return {
      FormControlConvValue: undefined,
    }
  },
  created() {
    if (this.$options.fieldValueConvert) {
      const valueProp =
        this.$options.model && this.$options.model.prop
          ? this.$options.model.prop
          : 'value'

      this.$watch(valueProp, (value: any) => {
        if (this.FormControlConvValue === value) {
          // Value has already been converted
          this.FormControlConvValue = undefined
        } else if (this.$options.fieldValueConvert) {
          const newValue = this.$options.fieldValueConvert.call(this, value)
          if (newValue !== value) {
            this.FormControlConvValue = newValue
            this.setOwnValue(newValue)
          }
        }
      })
    }
  },
  methods: {
    getFormContext(): MinimalFormContext {
      return typing.extended(
        this,
        typing.TypeArg<{
          FormContext: MinimalFormContext
        }>()
      ).FormContext
    },
    isDecoratedFormField(): boolean {
      return !!(this.getFormContext() && this.$attrs['data-__field'])
    },
    setOwnValue(value: any) {
      const eventType =
        this.$options.model && this.$options.model.event
          ? this.$options.model.event
          : 'change'

      this.$emit(eventType, value)
    },
    getOwnValue() {
      const valueProp =
        this.$options.model && this.$options.model.prop
          ? this.$options.model.prop
          : 'value'
      return (this as Record<string, any>)[valueProp]
    },
  },
})
