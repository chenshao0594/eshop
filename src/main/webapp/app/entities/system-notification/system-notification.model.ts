import { MerchantStore } from '../merchant-store';
import { User } from '../../shared';
export class SystemNotification {
    constructor(
        public id?: number,
        public endDate?: any,
        public key?: string,
        public startDate?: any,
        public value?: string,
        public merchantStore?: MerchantStore,
        public user?: User,
    ) {
    }
}
