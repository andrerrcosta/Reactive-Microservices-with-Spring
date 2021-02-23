
FROM node:14.15.4 As builder
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
# RUN npm i npm@latest -g
RUN npm install
COPY . .
RUN npm run build --prod --aot=true


FROM nginx:1.19
COPY --from=builder /usr/src/app/dist/zclient/ /usr/share/nginx/html

