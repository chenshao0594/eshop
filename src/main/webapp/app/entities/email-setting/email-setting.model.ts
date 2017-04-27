
const enum SMTPSecurityEnum {
    'PLAIN',
    'SSL',
    'STARTTLS'

};
export class EmailSetting {
    constructor(
        public id?: number,
        public host?: string,
        public port?: number,
        public smtpSecurity?: SMTPSecurityEnum,
        public fromAddress?: string,
        public userName?: string,
        public password?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
    }
}
