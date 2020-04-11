import { expect } from 'chai'
import { shallowMount } from '@vue/test-utils'
import Header from '@/components/Header.vue'

describe('Header.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'IMIS'
    const wrapper = shallowMount(Header, {
      stubs: ['a-layout-header', 'a-icon'],
    })
    expect(wrapper.text()).to.include(msg)
  })
})
