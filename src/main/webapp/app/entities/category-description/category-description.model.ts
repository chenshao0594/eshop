import { Category } from '../category';
import { Language } from '../language';
export class CategoryDescription {
    constructor(
        public id?: number,
        public categoryHighlight?: string,
        public title?: string,
        public metatagDescription?: string,
        public description?: string,
        public seUrl?: string,
        public metatagKeywords?: string,
        public name?: string,
        public metatagTitle?: string,
        public category?: Category,
        public language?: Language,
    ) {
    }
}
