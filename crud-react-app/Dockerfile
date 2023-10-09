FROM node:16
WORKDIR /build
COPY build /build
RUN npm install -g serve
RUN npm install -g pm2
CMD ["pm2", "serve", "/build","3000","--spa","--no-daemon" ]