export const randomizeProperties = (keys, object) => {
  keys.forEach(key => {
    if (typeof key === "string") {
      object[key] =
        Math.random()
          .toString(36)
          .substring(2, 15) +
        Math.random()
          .toString(36)
          .substring(2, 15);
    } else if (typeof key === "object" && key.type === "number") {
      object[key.key] = Math.round((Math.random() + 0.1) * 10000);
    }
  });
};
