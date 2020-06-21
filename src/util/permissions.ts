import Api from '@/api'
import { Api as ApiDefs } from '@/api/ImisSwaggerApi'

export interface ApiParams {
  path: string
  method: string
}
export type ApiFunction = (...args: any[]) => any

// Retrieve the request method and path for the given Swagger API function
export function queryApiParams(apiFunc: ApiFunction): ApiParams {
  /*
  The following piece of code is a kind of ugly hack to find out the
  request method and path used in the given Swagger API. It operates
  by executing the exact same function on an own API copy with a
  surrogate `request` function that fetches path and method parameters
  to be returned as this function's result.
  */

  // Step 1: Create API copy with surrogate `request` operation
  let resultParams = undefined as undefined | ApiParams
  const myApiDefs = new ApiDefs() as any
  myApiDefs.request = (path: string, method: string) => {
    resultParams = {
      path,
      method,
    }
  }

  // Step 2: Generate wildcard-parameters to be included in the function call
  const mockArgs = [] as string[]
  for (let i = 0; i < apiFunc.length - 1; i++) {
    mockArgs.push('*')
  }

  // Step 3: Do the function call on the own API copy
  myApiDefs.api[apiFunc.name](...mockArgs)

  if (!resultParams) {
    throw new Error('Could not extract request parameters from function')
  } else {
    return resultParams
  }
}

export async function checkAllowed(funcs: ApiFunction): Promise<boolean>
export async function checkAllowed(
  funcs: ApiFunction[] | undefined
): Promise<boolean[]>
export async function checkAllowed<
  T extends Record<string, ApiFunction>,
  R extends { [key in keyof T]: boolean }
>(funcs: T): Promise<R>
// export function checkAllowed(funcs: Record<string, ApiFunction>): Record<string, boolean>;
export async function checkAllowed(funcs: any): Promise<any> {
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
  const apiResult = (await Api.queryPermissionsUsingPost(
    Object.fromEntries(
      funcs.map((func: ApiFunction) => [func.name, queryApiParams(func)])
    )
  )) as Record<string, boolean>

  const result = [] as boolean[]
  for (let i = 0; i < funcs.length; i++) {
    result[i] = apiResult[funcs[i].name]
  }

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
