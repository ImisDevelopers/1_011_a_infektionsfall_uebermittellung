export const anonymizeProperties = (keys, obj) => {
  keys.forEach(key => {
    if (typeof key === "string" && obj[key]) {
      obj[key] = obj[key].substr(0, 1) + "**********";
    } else if (typeof key === "object" && key.type === "number" && obj[key]) {
      obj[key.key] = 11111;
    }
  });
};
