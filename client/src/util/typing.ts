/**
 * Typescript utility module.
 */

/// Type representing a Type parameter to be passed to a type-inferring function.
export type TypeArg<T> = T;

/**
 * TypeArg generator. This function may be called for any TypeArg function
 * argument to provide a type for type inferrence.
 *
 * Note that this function's sole purpose is type inferrence and no actual
 * object is created.
 */
export function TypeArg<T>(): TypeArg<T> {
  return null as unknown as TypeArg<T>
}

/**
 * Simple cast avoiding explicit conversion to unknown.
 */
export function cast<T>(arg: unknown, _?: TypeArg<T>) { return arg as T }

/**
 * Identity function telling the Typescript compiler that the supplied argument
 * is additionally supporting data and operations from the given extension type.
 * This function may be used in contexts where current type inferrence mechanisms
 * fail at resolving all of the functionality a type is actually capable of.
 *
 * An example use case is the use of mixins or injections for Vue components,
 * which currently cannot be sufficiently inferred.
 */
export function extended<T, ExtensionType>(obj: T, _: TypeArg<ExtensionType>): (T & ExtensionType) {
  return obj as (T & ExtensionType)
}
