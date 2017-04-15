export class Currency {
    constructor(
        public id?: number,
        public code?: string,
        public supported?: boolean,
        public name?: string,
        public currency?: string,
    ) {
        this.supported = false;
    }
}
