import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductRelationship } from './product-relationship.model';
import { ProductRelationshipService } from './product-relationship.service';
@Injectable()
export class ProductRelationshipPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productRelationshipService: ProductRelationshipService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productRelationshipService.find(id).subscribe((productRelationship) => {
                this.productRelationshipModalRef(component, productRelationship);
            });
        } else {
            return this.productRelationshipModalRef(component, new ProductRelationship());
        }
    }

    productRelationshipModalRef(component: Component, productRelationship: ProductRelationship): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productRelationship = productRelationship;
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
