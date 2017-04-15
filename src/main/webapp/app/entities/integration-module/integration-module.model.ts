export class IntegrationModule {
    constructor(
        public id?: number,
        public configDetails?: string,
        public customModule?: boolean,
        public type?: string,
        public code?: string,
        public regions?: string,
        public image?: string,
        public module?: string,
        public configuration?: string,
    ) {
        this.customModule = false;
    }
}
