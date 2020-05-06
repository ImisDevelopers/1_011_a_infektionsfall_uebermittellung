import Vue from 'vue'

// Realise camelCase prefixing, whereas the prefixed name starts with capital
function prefixed(name: string, prefix?: string): string {
  if (!prefix) {
    return name
  } else {
    return prefix + name.charAt(0).toUpperCase() + name.substring(1)
  }
}
function prefixedKeysObject(keys: {[x: string]: any}, prefix?: string): {[x: string]: any} {
  return Object.fromEntries((Object.entries(keys) as [string, string][]).map(
    (entry: [string, string]) => [prefixed(entry[0], prefix), entry[1]],
  ))
}
function prefixedKeysArray(keys: string[], prefix?: string): string[] {
  return keys.map((key: string) => prefixed(key, prefix))
}

declare interface FormGroupMixinType {
  $options: {
    name: string;
    inputKeys: string[];
  };
  $props: {
    inputKeyPrefix?: string;
    inputKeys: { [x: string]: string };
    inputProps: Record<string, any>;
  };
  $watch: any;
  $forceUpdate: () => void;

  formInputKey: (this: FormGroupMixinType, key: string) => string;
  FormContext: any;

  prefixedKeysObject: (this: FormGroupMixinType, keys: {[x: string]: any}, prefix?: string) => {[x: string]: any};
  prefixedKeysArray: (this: FormGroupMixinType, keys: string[], prefix?: string) => string[];
  getData(this: FormGroupMixinType, fieldNames?: string[], usesPrefixedKeys?: boolean): {[x: string]: any};
}

declare module 'vue/types/options' {
  interface ComponentOptions<V extends Vue> {
    inputKeys?: string[];
  }
}

export const FormGroupMixin = Vue.extend({
  props: {
    inputKeyPrefix: {
      default: undefined,
    },
    inputKeys: {
      default: () => ({}),
    },
    inputProps: {
      default(this: FormGroupMixinType) {
        return Object.fromEntries(
          this.$options.inputKeys!.map((key: string) => [key, {}]),
        )
      },
    },
  },
  inject: {
    FormContext: { default: () => ({}) },
  },
  mounted(this: FormGroupMixinType) {
    if (this.$options.inputKeys) {
      // Make sure all form items use selfUpdate; this is crucial for form items
      // in the group to be re-rendered correctly when new values are set
      this.$options.inputKeys.forEach((key: string) => {
        if (!this.FormContext.form.formItems[this.formInputKey(key)].itemSelfUpdate) {
          console.error(`[ ${this.$options.name} ]: ` +
            `\`itemSelfUpdate\` is not enabled for form item of \`${key}\`. ` +
            'This may lead to contents not being re-rendered when their ' +
            'value is modified by calling `setFieldsValue` on its ' +
            'containing form. To fix this, add `:selfUpdate="true"` to ' +
            `the \`a-form-item\` component containing the \`${key}\` control ` +
            'or add `:selfUpdate="true"` to the root `a-form` element.',
          )
        }
      })
    }
  },
  methods: {
    formInputKey(this: FormGroupMixinType, key: string): string {
      const propKeys = this.$props.inputKeys
      return Object.prototype.hasOwnProperty.call(propKeys, key)
        ? propKeys[key]
        : prefixed(key, this.$props.inputKeyPrefix)
    },
    prefixedKeysObject(this: FormGroupMixinType, keys: {[x: string]: any}): {[x: string]: any} {
      return prefixedKeysObject(keys, this.$props.inputKeyPrefix)
    },
    prefixedKeysArray(this: FormGroupMixinType, keys: string[]): string[] {
      return prefixedKeysArray(keys, this.$props.inputKeyPrefix)
    },
    setData(this: FormGroupMixinType, data: {[x: string]: any}, usesPrefixedKeys: boolean | undefined) {
      if (!this.$options.inputKeys) {
        throw new Error(`[ ${this.$options.name} ]: \`setData\` not supported`)
      }

      if (!usesPrefixedKeys) {
        data = this.prefixedKeysObject(data)
      }
      if (data) {
        const prefixedInputKeys = prefixedKeysArray(this.$options.inputKeys)
        data = Object.fromEntries(Object.entries(data).filter(
          (value: [string, any]) => prefixedInputKeys.includes(value[0]),
        ))
      }
      this.FormContext.form.setFieldsValue(data)
    },
    getData(this: FormGroupMixinType, fieldNames?: string[], usesPrefixedKeys?: boolean): {[x: string]: any} {
      if (!this.$options.inputKeys) {
        throw new Error(`[ ${this.$options.name} ]: \`getData\` not supported`)
      }

      if (fieldNames !== undefined && !usesPrefixedKeys) {
        fieldNames = this.prefixedKeysArray(fieldNames)
      }

      const permittedFieldNames = prefixedKeysArray(this.$options.inputKeys)
      if (fieldNames === undefined) {
        fieldNames = permittedFieldNames
      } else {
        fieldNames = fieldNames.filter(
          (fieldName: string) => permittedFieldNames.includes(fieldName),
        )
      }
      return this.FormContext.form.getFieldsValue(fieldNames)
    },
    getSingleValue(this: FormGroupMixinType, fieldName: string, usesPrefixedKeys?: boolean): any | undefined {
      const key = usesPrefixedKeys ? fieldName : this.formInputKey(fieldName)
      return this.getData([key], true)[key]
    },
  },
})

declare interface FormControlMixinType {
  $options: {
    fieldValueConvert?: (val: any) => any;
    forwardEvents?: string[];
    model?: {
      prop?: string;
      event?: string;
    };
  };
  $attrs: {
    'data-__field'?: {
      name: string;
      value: any;
    };
  };
  $emit: (event: string, param?: any) => void;
  $watch: (expr: string, cb: (val: any) => void) => void;

  FormContext?: {
    form: {
      setFieldsValue: (fields: { [x: string]: any }) => void;
    };
  };

  FormControlConvValue: any;
  fieldName: string | null;
  isDecoratedFormField(): boolean;
  getOwnValue(): any;
  setOwnValue(val: any): void;
}

declare module 'vue/types/options' {
  interface ComponentOptions<V extends Vue> {
    fieldValueConvert?(value: any): any;
  }
}

export const FormControlMixin = Vue.extend({
  inject: {
    FormContext: { default: null },
  },
  computed: {
    fieldName(this: FormControlMixinType): string | null {
      if (this.FormContext && this.$attrs['data-__field']) {
        return this.$attrs['data-__field'].name
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
  created(this: FormControlMixinType) {
    if (this.$options.fieldValueConvert) {
      const valueProp = (this.$options.model && this.$options.model.prop)
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
    isDecoratedFormField(this: FormControlMixinType): boolean {
      return !!(this.FormContext && this.$attrs['data-__field'])
    },
    setOwnValue(this: FormControlMixinType, value: any) {
      const eventType = (this.$options.model && this.$options.model.event)
        ? this.$options.model.event
        : 'change'

      this.$emit(eventType, value)
    },
    getOwnValue(this: FormControlMixinType) {
      const valueProp = (this.$options.model && this.$options.model.prop)
        ? this.$options.model.prop
        : 'value'
      return (this as {[x: string]: any})[valueProp]
    },
  },
})
