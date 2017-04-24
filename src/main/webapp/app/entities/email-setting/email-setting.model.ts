
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
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
    ) {
    }
}
