import Api from '@/api'

export interface ApiParams {
  path: string;
  method: string;
}
export type ApiFunction = (...args: any[]) => any;

export function queryApiParams(apiFunc: ApiFunction): ApiParams {
  // This client will be used as the this-context of apiFunc; this way,
  // we can intercept the path and method used when executing the REST
  // call that we need for checking permissions
  const mockClient = {
    ...Api,
    apiParams: undefined as (undefined | ApiParams),
    request(path: string, method: string) {
      this.apiParams = {
        path,
        method,
      }
    },
  }
  // Make sure additional parameters are wildcard-strings
  const mockArgs = []
  for (let i = 0; i < apiFunc.length - 1; i++) {
    mockArgs.push('*')
  }
  apiFunc.call(mockClient, ...mockArgs)

  if (!mockClient.apiParams) {
    throw new Error('Could not extract api parameters from function. Is it a legal API-function?')
  } else {
    return mockClient.apiParams
  }
}

export function checkAllowed(funcs: ApiFunction): boolean;
export function checkAllowed(funcs: ApiFunction[] | undefined): boolean[];
export function checkAllowed<T extends Record<string, ApiFunction>, R extends { [key in keyof T]: boolean }>(funcs: T): R;
// export function checkAllowed(funcs: Record<string, ApiFunction>): Record<string, boolean>;
export function checkAllowed(funcs: any): any {
  const resultLabels = [] as string[]
  let singleResult = false
  if (!funcs) {
    funcs = Object.values((Api as any).api)
  }
  if (typeof funcs === 'object') {
    const funcsArr = [] as ApiFunction[]
    Object.entries(funcs).forEach((entry: [string, any]) => {
      resultLabels.push(entry[0])
      funcsArr.push(entry[1])
    })

    funcs = funcsArr
  } else if (funcs && !Array.isArray(funcs)) {
    funcs = [funcs]
    singleResult = true
  }

  // const params = funcs.map(queryApiParams)

  // Make the permission asking request
  /* TODO */
  const result = funcs.map(() => true)

  if (singleResult) {
    return result[0]
  } else {
    if (resultLabels) {
      const forReturn = {} as Record<string, boolean>
      for (let i = 0; i < resultLabels.length; i++) {
        forReturn[resultLabels[i]] = result[i]
      }
      return forReturn
    } else {
      return result
    }
  }
}
