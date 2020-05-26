import Header from '@/components/structural/Header.vue'
import { shallowMount } from '@vue/test-utils'
import { expect } from 'chai'

describe('Header.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'IMIS'
    const wrapper = shallowMount(Header, {
      stubs: ['a-layout-header', 'a-icon'],
    })
    expect(wrapper.text()).to.include(msg)
  })
})
