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
                if (emailSetting.createdDate) {
                    emailSetting.createdDate = {
                        year: emailSetting.createdDate.getFullYear(),
                        month: emailSetting.createdDate.getMonth() + 1,
                        day: emailSetting.createdDate.getDate()
                    };
                }
                if (emailSetting.lastModifiedDate) {
                    emailSetting.lastModifiedDate = {
                        year: emailSetting.lastModifiedDate.getFullYear(),
                        month: emailSetting.lastModifiedDate.getMonth() + 1,
                        day: emailSetting.lastModifiedDate.getDate()
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
