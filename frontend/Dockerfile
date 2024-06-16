# Stage 1: Build stage
FROM node:20-alpine3.19 as build-stage
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application files
COPY . .

# Setup environment variables
RUN cp .env.example .env

# Build the Vue.js application
RUN npm run build

# Stage 2: Production stage
FROM nginx:stable-alpine as production-stage

# Copy built files from the build stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copy the nginx configuration file
COPY assets/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]