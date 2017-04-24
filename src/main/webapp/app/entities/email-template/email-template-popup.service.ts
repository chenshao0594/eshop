import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmailTemplate } from './email-template.model';
import { EmailTemplateService } from './email-template.service';
@Injectable()
export class EmailTemplatePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private emailTemplateService: EmailTemplateService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.emailTemplateService.find(id).subscribe((emailTemplate) => {
                if (emailTemplate.created_date) {
                    emailTemplate.created_date = {
                        year: emailTemplate.created_date.getFullYear(),
                        month: emailTemplate.created_date.getMonth() + 1,
                        day: emailTemplate.created_date.getDate()
                    };
                }
                if (emailTemplate.last_modified_date) {
                    emailTemplate.last_modified_date = {
                        year: emailTemplate.last_modified_date.getFullYear(),
                        month: emailTemplate.last_modified_date.getMonth() + 1,
                        day: emailTemplate.last_modified_date.getDate()
                    };
                }
                this.emailTemplateModalRef(component, emailTemplate);
            });
        } else {
            return this.emailTemplateModalRef(component, new EmailTemplate());
        }
    }

    emailTemplateModalRef(component: Component, emailTemplate: EmailTemplate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.emailTemplate = emailTemplate;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
