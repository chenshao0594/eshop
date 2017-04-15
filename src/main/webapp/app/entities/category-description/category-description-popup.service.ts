import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CategoryDescription } from './category-description.model';
import { CategoryDescriptionService } from './category-description.service';
@Injectable()
export class CategoryDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private categoryDescriptionService: CategoryDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.categoryDescriptionService.find(id).subscribe((categoryDescription) => {
                this.categoryDescriptionModalRef(component, categoryDescription);
            });
        } else {
            return this.categoryDescriptionModalRef(component, new CategoryDescription());
        }
    }

    categoryDescriptionModalRef(component: Component, categoryDescription: CategoryDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.categoryDescription = categoryDescription;
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
