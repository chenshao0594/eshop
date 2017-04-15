export class ShippingOrigin {
    constructor(
        public id?: number,
        public city?: string,
        public postalCode?: string,
        public address?: string,
        public active?: boolean,
        public state?: string,
    ) {
        this.active = false;
    }
}
