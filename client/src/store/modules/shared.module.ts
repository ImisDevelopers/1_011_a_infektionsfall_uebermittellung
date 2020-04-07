// import { Module } from 'vuex'
// import { State } from '@/store'
//
// export interface SharedState {
//   loadingStatus: number;
// }
//
// export const sharedModule: Module<SharedState, State> = {
//   namespaced: true,
//   state: {
//     loadingStatus: 0,
//   },
//   mutations: {
//     startedLoading (state) {
//       ++state.loadingStatus
//     },
//     finishedLoading (state) {
//       --state.loadingStatus
//       if (state.loadingStatus < 0) {
//         console.log('Loadingstatus is negative!!!!!!!')
//         state.loadingStatus = Math.max(state.loadingStatus, 0)
//       }
//     },
//   },
//   actions: {},
//
//   getters: {
//     loadingStatus: function (state: SharedState) {
//       return state.loadingStatus
//     },
//   },
//
// }
