export const anonymizeProperties = (keys, obj) => {
  keys.forEach(key => {
    if (typeof key === "string") {
      obj[key] = '**********'
    } else if (typeof key === "object" && key.type === "number") {
      obj[key.key] = 11111
    }
  });
  console.log(obj)
};
