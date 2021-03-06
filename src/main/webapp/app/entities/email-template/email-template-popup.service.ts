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
                if (emailTemplate.createdDate) {
                    emailTemplate.createdDate = {
                        year: emailTemplate.createdDate.getFullYear(),
                        month: emailTemplate.createdDate.getMonth() + 1,
                        day: emailTemplate.createdDate.getDate()
                    };
                }
                if (emailTemplate.lastModifiedDate) {
                    emailTemplate.lastModifiedDate = {
                        year: emailTemplate.lastModifiedDate.getFullYear(),
                        month: emailTemplate.lastModifiedDate.getMonth() + 1,
                        day: emailTemplate.lastModifiedDate.getDate()
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
