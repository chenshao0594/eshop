import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmailSetting } from './email-setting.model';
import { EmailSettingService } from './email-setting.service';
@Injectable()
export class EmailSettingPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private emailSettingService: EmailSettingService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.emailSettingService.find(id).subscribe((emailSetting) => {
                if (emailSetting.created_date) {
                    emailSetting.created_date = {
                        year: emailSetting.created_date.getFullYear(),
                        month: emailSetting.created_date.getMonth() + 1,
                        day: emailSetting.created_date.getDate()
                    };
                }
                if (emailSetting.last_modified_date) {
                    emailSetting.last_modified_date = {
                        year: emailSetting.last_modified_date.getFullYear(),
                        month: emailSetting.last_modified_date.getMonth() + 1,
                        day: emailSetting.last_modified_date.getDate()
                    };
                }
                this.emailSettingModalRef(component, emailSetting);
            });
        } else {
            return this.emailSettingModalRef(component, new EmailSetting());
        }
    }

    emailSettingModalRef(component: Component, emailSetting: EmailSetting): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.emailSetting = emailSetting;
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
