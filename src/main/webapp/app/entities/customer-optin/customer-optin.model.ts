import { Optin } from '../optin';
export class CustomerOptin {
    constructor(
        public id?: number,
        public optinDate?: any,
        public email?: string,
        public value?: string,
        public firstName?: string,
        public lastName?: string,
        public optin?: Optin,
    ) {
    }
}
