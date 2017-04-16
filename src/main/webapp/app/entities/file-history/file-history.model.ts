import { MerchantStore } from '../merchant-store';
export class FileHistory {
    constructor(
        public id?: number,
        public dateAdded?: any,
        public dateDeleted?: any,
        public downloadCount?: number,
        public fileId?: number,
        public accountedDate?: any,
        public filesize?: number,
        public store?: MerchantStore,
    ) {
    }
}
