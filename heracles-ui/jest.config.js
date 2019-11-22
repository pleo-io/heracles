module.exports = {
    roots: ['<rootDir>/src'],
    transform: {
        '^.+\\.tsx?$': 'ts-jest',
    },
    testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
    moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
    globals: {
        "ts-jest": {
            tsConfig: "tsconfig.json"
        }
    },
    "moduleNameMapper": {
        "^.+\\.(css|less|scss)$": "identity-obj-proxy"
    },
    testPathIgnorePatterns: ['__tests__/*', 'test-setup.tsx']
}
