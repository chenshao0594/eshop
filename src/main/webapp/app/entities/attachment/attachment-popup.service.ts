import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Attachment } from './attachment.model';
import { AttachmentService } from './attachment.service';
@Injectable()
export class AttachmentPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private attachmentService: AttachmentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.attachmentService.find(id).subscribe((attachment) => {
                if (attachment.created_date) {
                    attachment.created_date = {
                        year: attachment.created_date.getFullYear(),
                        month: attachment.created_date.getMonth() + 1,
                        day: attachment.created_date.getDate()
                    };
                }
                if (attachment.last_modified_date) {
                    attachment.last_modified_date = {
                        year: attachment.last_modified_date.getFullYear(),
                        month: attachment.last_modified_date.getMonth() + 1,
                        day: attachment.last_modified_date.getDate()
                    };
                }
                this.attachmentModalRef(component, attachment);
            });
        } else {
            return this.attachmentModalRef(component, new Attachment());
        }
    }

    attachmentModalRef(component: Component, attachment: Attachment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.attachment = attachment;
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
