/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumDeleteDialogComponent } from 'app/entities/kriterijum/kriterijum-delete-dialog.component';
import { KriterijumService } from 'app/entities/kriterijum/kriterijum.service';

describe('Component Tests', () => {
    describe('Kriterijum Management Delete Component', () => {
        let comp: KriterijumDeleteDialogComponent;
        let fixture: ComponentFixture<KriterijumDeleteDialogComponent>;
        let service: KriterijumService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumDeleteDialogComponent]
            })
                .overrideTemplate(KriterijumDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KriterijumDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KriterijumService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
