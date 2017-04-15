
const enum ContentType {
    'BOX',
    'PAGE',
    'SECTION'

};

const enum ContentPosition {
    'LEFT',
    'RIGHT'

};
import { ContentDescription } from '../content-description';
export class Content {
    constructor(
        public id?: number,
        public sortOrder?: number,
        public productGroup?: string,
        public contentType?: ContentType,
        public code?: string,
        public visible?: boolean,
        public contentPosition?: ContentPosition,
        public descriptions?: ContentDescription,
    ) {
        this.visible = false;
    }
}
