
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
import { MerchantStore } from '../merchant-store';
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
        public merchantStore?: MerchantStore,
    ) {
        this.visible = false;
    }
}
