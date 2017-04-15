import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ContentDescription } from './content-description.model';
import { ContentDescriptionService } from './content-description.service';
@Injectable()
export class ContentDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private contentDescriptionService: ContentDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.contentDescriptionService.find(id).subscribe((contentDescription) => {
                this.contentDescriptionModalRef(component, contentDescription);
            });
        } else {
            return this.contentDescriptionModalRef(component, new ContentDescription());
        }
    }

    contentDescriptionModalRef(component: Component, contentDescription: ContentDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contentDescription = contentDescription;
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
