import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductPrice } from './product-price.model';
import { ProductPricePopupService } from './product-price-popup.service';
import { ProductPriceService } from './product-price.service';

@Component({
    selector: 'jhi-product-price-delete-dialog',
    templateUrl: './product-price-delete-dialog.component.html'
})
export class ProductPriceDeleteDialogComponent {

    productPrice: ProductPrice;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productPriceService: ProductPriceService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productPrice', 'productPriceType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productPriceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productPriceListModification',
                content: 'Deleted an productPrice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-price-delete-popup',
    template: ''
})
export class ProductPriceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productPricePopupService: ProductPricePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productPricePopupService
                .open(ProductPriceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
