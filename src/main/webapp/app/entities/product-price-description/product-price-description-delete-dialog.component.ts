import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductPriceDescription } from './product-price-description.model';
import { ProductPriceDescriptionPopupService } from './product-price-description-popup.service';
import { ProductPriceDescriptionService } from './product-price-description.service';

@Component({
    selector: 'jhi-product-price-description-delete-dialog',
    templateUrl: './product-price-description-delete-dialog.component.html'
})
export class ProductPriceDescriptionDeleteDialogComponent {

    productPriceDescription: ProductPriceDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productPriceDescriptionService: ProductPriceDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productPriceDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productPriceDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productPriceDescriptionListModification',
                content: 'Deleted an productPriceDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-price-description-delete-popup',
    template: ''
})
export class ProductPriceDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productPriceDescriptionPopupService: ProductPriceDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productPriceDescriptionPopupService
                .open(ProductPriceDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
