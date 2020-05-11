module.exports = {
  root: true,
  parser: 'vue-eslint-parser',
  plugins: ['@typescript-eslint'],
  env: {
    node: true,
  },
  extends: [
    'plugin:@typescript-eslint/recommended',
    'plugin:vue/essential',
    '@vue/standard',
    '@vue/typescript/recommended',
  ],
  parserOptions: {
    parser: '@typescript-eslint/parser',
    ecmaVersion: 2020,
  },
  rules: {
    // '@typescript-eslint/rule-name': 'error',
    '@typescript-eslint/no-extra-semi': 'error',

    'no-console': 'off', // process.env.NODE_ENV === 'production' ? 'error' :
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'comma-dangle': [
      'error',
      {
        arrays: 'always-multiline',
        objects: 'always-multiline',
        imports: 'always-multiline',
        exports: 'always-multiline',
        functions: 'always-multiline',
      },
    ],
    'space-before-function-paren': ['error', 'never'],
    'object-curly-spacing': ['error', 'always'],
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
