/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumComponent } from 'app/entities/kriterijum/kriterijum.component';
import { KriterijumService } from 'app/entities/kriterijum/kriterijum.service';
import { Kriterijum } from 'app/shared/model/kriterijum.model';

describe('Component Tests', () => {
    describe('Kriterijum Management Component', () => {
        let comp: KriterijumComponent;
        let fixture: ComponentFixture<KriterijumComponent>;
        let service: KriterijumService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumComponent],
                providers: []
            })
                .overrideTemplate(KriterijumComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KriterijumComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KriterijumService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Kriterijum(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.kriterijums[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
