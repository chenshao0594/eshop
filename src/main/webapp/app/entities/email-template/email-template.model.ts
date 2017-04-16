export class EmailTemplate {
    constructor(
        public id?: number,
        public name?: string,
        public subject?: string,
        public content?: any,
        public actionKey?: string,
    ) {
    }
}
