# syntax=docker/dockerfile:1
FROM node:18
WORKDIR /app
COPY package*json ./
COPY src ./src
COPY .prettier* ./
COPY tsconfig.json ./

RUN --mount=type=cache,target=/root/.npm \
    npm install
RUN npm run verify

ENTRYPOINT ["npm", "run", "start"]
