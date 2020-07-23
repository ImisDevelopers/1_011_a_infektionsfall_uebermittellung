<template>
  <a-modal
    :visible="visible"
    title="Institution ändern"
    okText="Speichern"
    cancelText="Abbrechen"
    @cancel="
      () => {
        $emit('cancel')
      }
    "
    @ok="save"
  >
    <a-form :form="form" :layout="'vertical'">
      <a-form-item label="Name">
        <a-input
          v-decorator="[
            'name',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte Namen eingeben',
                },
              ],
              initialValue: institution.name,
            },
          ]"
        />
      </a-form-item>
      <a-form-item label="E-Mail">
        <a-input v-decorator="['email', { initialValue: institution.email }]" />
      </a-form-item>
      <a-form-item label="Telefonnummer">
        <a-input
          v-decorator="[
            'phoneNumber',
            { initialValue: institution.phoneNumber },
          ]"
        />
      </a-form-item>
      <div style="display: flex;">
        <a-form-item label="Straße">
          <a-input
            v-decorator="[
              'street',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte Straße eingeben',
                  },
                ],
                initialValue: institution.street,
              },
            ]"
          />
        </a-form-item>
        <a-form-item
          label="Hausnummer"
          style="flex: 0 0 125px; margin-left: 15px;"
        >
          <a-input
            v-decorator="[
              'houseNumber',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte Hausnummer eingeben',
                  },
                ],
                initialValue: institution.houseNumber,
              },
            ]"
          />
        </a-form-item>
      </div>
      <div style="display: flex;">
        <PlzInput
          :decorator="[
            'zip',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte PLZ eingeben',
                },
              ],
              initialValue: institution.zip,
            },
          ]"
          @plzChanged="setPLZ"
          style="margin-right: 15px;"
        />
        <a-form-item label="Stadt">
          <a-input
            v-decorator="[
              'city',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte Stadt eingeben',
                  },
                ],
                initialValue: institution.city,
              },
            ]"
          />
        </a-form-item>
      </div>
      <a-form-item label="Kommentar">
        <a-input
          v-decorator="['comment', { initialValue: institution.comment }]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'
import { Plz } from '@/util/plz-service'
import PlzInput from '@/components/inputs/PlzInput.vue'

export default Vue.extend({
  name: 'Account',
  props: ['visible', 'institution'],
  components: {
    PlzInput,
  },
  data() {
    return {
      form: this.$form.createForm(this, { name: 'institution' }),
    }
  },
  methods: {
    ...authMapper.mapActions({
      updateInstitution: 'updateInstitution',
    }),
    setPLZ(plz: Plz) {
      this.form.setFieldsValue({
        zip: plz.fields.plz,
        city: plz.fields.note,
      })
    },
    save() {
      this.form.validateFields((err: any, values: any) => {
        if (err) {
          console.warn(err)
          return
        }
        this.updateInstitution({
          ...values,
          institutionType: this.institution.institutionType,
          id: this.institution.id,
        })
          .then(() => {
            this.$notification.success({
              message: 'Institution erfolgreich geändert',
              description: '',
            })
            this.form.resetFields()
            this.$emit('create')
          })
          .catch((error: Error) => {
            this.$notification.error({
              message: 'Institution konnte nicht geändert werden.',
              description: error.message,
            })
          })
      })
    },
  },
})
</script>

<style scoped lang="scss"></style>
