import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { MerchantConfiguration } from './merchant-configuration.model';
import { MerchantConfigurationPopupService } from './merchant-configuration-popup.service';
import { MerchantConfigurationService } from './merchant-configuration.service';

@Component({
    selector: 'jhi-merchant-configuration-delete-dialog',
    templateUrl: './merchant-configuration-delete-dialog.component.html'
})
export class MerchantConfigurationDeleteDialogComponent {

    merchantConfiguration: MerchantConfiguration;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private merchantConfigurationService: MerchantConfigurationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['merchantConfiguration', 'merchantConfigurationType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantConfigurationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'merchantConfigurationListModification',
                content: 'Deleted an merchantConfiguration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchant-configuration-delete-popup',
    template: ''
})
export class MerchantConfigurationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantConfigurationPopupService: MerchantConfigurationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.merchantConfigurationPopupService
                .open(MerchantConfigurationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
