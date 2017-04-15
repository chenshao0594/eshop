import { OrderProduct } from '../order-product';
export class OrderProductDownload {
    constructor(
        public id?: number,
        public maxdays?: number,
        public downloadCount?: number,
        public orderProductFilename?: string,
        public orderProduct?: OrderProduct,
    ) {
    }
}
