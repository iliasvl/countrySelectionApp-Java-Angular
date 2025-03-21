# Use a base image with Node.js
FROM node:18

# Set the working directory
WORKDIR /usr/src/app

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the entire Angular app
COPY . .

# Build the Angular app with SSR
RUN npm run build:ssr

# Expose the port the app runs on
EXPOSE 4000

# Start the SSR server
# CMD ["npm", "run", "serve:ssr"]
CMD ["sh", "-c", "node dist/country-travel-tracker/server/main.js && tail -f /dev/null"]
