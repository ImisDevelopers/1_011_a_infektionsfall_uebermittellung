export const randomizeProperties = (keys, object) => {
  keys.forEach(key => {
    object[key] =
      Math.random()
        .toString(36)
        .substring(2, 15) +
      Math.random()
        .toString(36)
        .substring(2, 15);
  });
};
