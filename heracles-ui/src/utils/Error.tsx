export default class Error {
    public key: string = "";
    public error: string = "";

    constructor(key: string, error: string) {
        this.key = key;
        this.error = error;
    }
}
