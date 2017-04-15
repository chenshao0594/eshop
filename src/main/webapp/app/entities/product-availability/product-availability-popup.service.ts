import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductAvailability } from './product-availability.model';
import { ProductAvailabilityService } from './product-availability.service';
@Injectable()
export class ProductAvailabilityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productAvailabilityService: ProductAvailabilityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productAvailabilityService.find(id).subscribe((productAvailability) => {
                if (productAvailability.productDateAvailable) {
                    productAvailability.productDateAvailable = {
                        year: productAvailability.productDateAvailable.getFullYear(),
                        month: productAvailability.productDateAvailable.getMonth() + 1,
                        day: productAvailability.productDateAvailable.getDate()
                    };
                }
                this.productAvailabilityModalRef(component, productAvailability);
            });
        } else {
            return this.productAvailabilityModalRef(component, new ProductAvailability());
        }
    }

    productAvailabilityModalRef(component: Component, productAvailability: ProductAvailability): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productAvailability = productAvailability;
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
