import { Content } from '../content';
import { Language } from '../language';
export class ContentDescription {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public metatagTitle?: string,
        public metatagDescription?: string,
        public name?: string,
        public seUrl?: string,
        public metatagKeywords?: string,
        public content?: Content,
        public language?: Language,
    ) {
    }
}
