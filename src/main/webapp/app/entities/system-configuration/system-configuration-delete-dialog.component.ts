import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SystemConfiguration } from './system-configuration.model';
import { SystemConfigurationPopupService } from './system-configuration-popup.service';
import { SystemConfigurationService } from './system-configuration.service';

@Component({
    selector: 'jhi-system-configuration-delete-dialog',
    templateUrl: './system-configuration-delete-dialog.component.html'
})
export class SystemConfigurationDeleteDialogComponent {

    systemConfiguration: SystemConfiguration;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private systemConfigurationService: SystemConfigurationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['systemConfiguration']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.systemConfigurationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'systemConfigurationListModification',
                content: 'Deleted an systemConfiguration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-system-configuration-delete-popup',
    template: ''
})
export class SystemConfigurationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private systemConfigurationPopupService: SystemConfigurationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.systemConfigurationPopupService
                .open(SystemConfigurationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
