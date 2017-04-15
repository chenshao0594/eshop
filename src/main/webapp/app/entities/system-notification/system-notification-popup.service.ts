import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SystemNotification } from './system-notification.model';
import { SystemNotificationService } from './system-notification.service';
@Injectable()
export class SystemNotificationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private systemNotificationService: SystemNotificationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.systemNotificationService.find(id).subscribe((systemNotification) => {
                if (systemNotification.endDate) {
                    systemNotification.endDate = {
                        year: systemNotification.endDate.getFullYear(),
                        month: systemNotification.endDate.getMonth() + 1,
                        day: systemNotification.endDate.getDate()
                    };
                }
                if (systemNotification.startDate) {
                    systemNotification.startDate = {
                        year: systemNotification.startDate.getFullYear(),
                        month: systemNotification.startDate.getMonth() + 1,
                        day: systemNotification.startDate.getDate()
                    };
                }
                this.systemNotificationModalRef(component, systemNotification);
            });
        } else {
            return this.systemNotificationModalRef(component, new SystemNotification());
        }
    }

    systemNotificationModalRef(component: Component, systemNotification: SystemNotification): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.systemNotification = systemNotification;
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
