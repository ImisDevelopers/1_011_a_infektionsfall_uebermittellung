module.exports = {
  root: true,
  parser: 'vue-eslint-parser',
  plugins: ['@typescript-eslint'],
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/essential',
    'eslint:recommended',
    '@vue/typescript/recommended',
    '@vue/prettier',
    '@vue/prettier/@typescript-eslint',
  ],
  parserOptions: {
    parser: '@typescript-eslint/parser',
    ecmaVersion: 2020,
  },
  rules: {
    '@typescript-eslint/no-extra-semi': 'error',
    '@typescript-eslint/no-explicit-any': 'off',
  },
  overrides: [
    {
      files: [
        'src/**/*.{js,ts,vue}',
        '**/__tests__/*.{ts,js,vue}',
        '**/tests/unit/**/*.spec.{ts,js,vue}',
      ],
      env: {
        mocha: true,
      },
    },
  ],
}
