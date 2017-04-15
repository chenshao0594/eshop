import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductOption } from './product-option.model';
import { ProductOptionPopupService } from './product-option-popup.service';
import { ProductOptionService } from './product-option.service';

@Component({
    selector: 'jhi-product-option-delete-dialog',
    templateUrl: './product-option-delete-dialog.component.html'
})
export class ProductOptionDeleteDialogComponent {

    productOption: ProductOption;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productOptionService: ProductOptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOption']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productOptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productOptionListModification',
                content: 'Deleted an productOption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-option-delete-popup',
    template: ''
})
export class ProductOptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionPopupService: ProductOptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productOptionPopupService
                .open(ProductOptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
