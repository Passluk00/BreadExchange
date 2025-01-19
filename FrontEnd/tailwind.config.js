/** @type {import('tailwindcss').Config} */
module.exports = {

  content: [
    "./src/**/*.{html,ts}",
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      screens: {
        'below-745': {'max': '744px'},
        custom: '995px',
        custom2: '1200px'
      }
    },
  },
  plugins: [
    require('flowbite/plugin') // add this line
  ],
}

