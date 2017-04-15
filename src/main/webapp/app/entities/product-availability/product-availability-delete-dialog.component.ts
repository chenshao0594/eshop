import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductAvailability } from './product-availability.model';
import { ProductAvailabilityPopupService } from './product-availability-popup.service';
import { ProductAvailabilityService } from './product-availability.service';

@Component({
    selector: 'jhi-product-availability-delete-dialog',
    templateUrl: './product-availability-delete-dialog.component.html'
})
export class ProductAvailabilityDeleteDialogComponent {

    productAvailability: ProductAvailability;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productAvailabilityService: ProductAvailabilityService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productAvailability']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productAvailabilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productAvailabilityListModification',
                content: 'Deleted an productAvailability'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-availability-delete-popup',
    template: ''
})
export class ProductAvailabilityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productAvailabilityPopupService: ProductAvailabilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productAvailabilityPopupService
                .open(ProductAvailabilityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
