import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductType } from './product-type.model';
import { ProductTypePopupService } from './product-type-popup.service';
import { ProductTypeService } from './product-type.service';

@Component({
    selector: 'jhi-product-type-delete-dialog',
    templateUrl: './product-type-delete-dialog.component.html'
})
export class ProductTypeDeleteDialogComponent {

    productType: ProductType;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productTypeService: ProductTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productTypeListModification',
                content: 'Deleted an productType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-type-delete-popup',
    template: ''
})
export class ProductTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productTypePopupService: ProductTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productTypePopupService
                .open(ProductTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
