FROM node:alpine

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
RUN yarn global add  @vue/cli@3.7.0

# install and cache app dependencies
COPY package.json /app/package.json
RUN yarn install
COPY . .
RUN yarn run lint
RUN yarn run build


# start app
EXPOSE 8080
CMD ["yarn", "serve"]